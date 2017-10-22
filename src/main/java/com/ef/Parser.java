package com.ef;

import com.ef.entity.BlockedIpAddress;
import com.ef.entity.LogEntry;
import com.ef.repository.BlockedIpAddressRepository;
import com.ef.repository.LogEntryRepository;
import com.ef.util.CommandLineArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class Parser {
    private static final String USAGE = "Usage:\r\n" +
            "Load request data into a DB: java -jar \"parser.jar\" --file=access.log\r\n" +
            "Analyze request logs: java -jar \"parser.jar\" --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100\r\n";

    private static final String IP_ADDRESS_BLOCKING_INFO_PATTERN = "The IP address %s had %d request(s) and will be blocked.";
    private static final String IP_ADDRESS_BLOCKING_REASON_PATTERN = "Performed %3$d request(s) between %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL and %2$tY-%2$tm-%2$td %2$tH:%2$tM:%2$tS.%2$tL";

    private static LogEntryRepository logEntryRepository;
    private static BlockedIpAddressRepository blockedIpAddressRepository;

	public static void main(String[] args) {
        print("============================================================");
        print("Starting Parser app ...");

        try{
            // parse command line arguments
            CommandLineArguments commandLineArguments = CommandLineArguments.parse(args);

            // init Spring things
            initApplication(args);

            // if log file name has been provided
            if(commandLineArguments.getLogFileName() != null) {
                // load it to DB
                loadDataFromFile(commandLineArguments.getLogFileName());
            }
            // if IP-address-check arguments have been provided
            if(commandLineArguments.isIpAddressCheckArgumentsPresent()) {
                // retrieve list of pairs [ipAddress, requestsNumberFromTheIP]
                List<Object[]> ipAddresses = getIpAddressesByParameters(commandLineArguments);

                List<BlockedIpAddress> blockedIpAddresses = new ArrayList<>();

                if(ipAddresses.size() != 0) {
                    print("The list of suspicious IP addresses:");
                    // for each IP in the resulting list
                    ipAddresses.forEach((ipEntry) -> {
                        // print it
                        print(String.format(IP_ADDRESS_BLOCKING_INFO_PATTERN, ipEntry[0], ipEntry[1]));

                        // schedule it for adding into a BlockedIpAddress table
                        blockedIpAddresses.add(new BlockedIpAddress(
                            (String)ipEntry[0],
                            LocalDateTime.now(),
                            String.format(
                                IP_ADDRESS_BLOCKING_REASON_PATTERN,
                                commandLineArguments.getStartDate(),
                                commandLineArguments.getStartDate().plusHours(commandLineArguments.getDuration().toHours()),
                                ipEntry[1])));
                    });

                    // save all suspicious IPs into a DB
                    blockedIpAddressRepository.save(blockedIpAddresses);

                    print("All these IP addresses have been blocked successfully!");
                } else {
                    print("No suspicious IP addresses found for the given time range and threshold!");
                }

            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(USAGE);
        }

        print("Closing Parser app");
        print("============================================================");
	}

    /**
     * Inits Sprint context and beans
     * @param args
     */
    private static void initApplication(String[] args) {
        print("Init Spring context");

        ApplicationContext applicationContext = SpringApplication.run(Parser.class, args);

        logEntryRepository = applicationContext.getBean(LogEntryRepository.class);
        blockedIpAddressRepository = applicationContext.getBean(BlockedIpAddressRepository.class);

        print("Spring context had been initialized");
    }

    /**
     * Loads access log file into a DB
     * @param fileName
     */
    private static void loadDataFromFile(String fileName) {
        print("Loading data from a file into DB ...");

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<LogEntry> parsedLogEntries = new ArrayList<>();

            stream.forEach((line) -> {
                parsedLogEntries.add(LogEntry.parse(line));
            });

            logEntryRepository.save(parsedLogEntries);

            print("Log entries have been loaded successfully!");

        } catch (IOException e) {
            System.err.println("Cannot load data from file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets pairs of IP address and its requests amount specified by commandLineArguments
     * @param commandLineArguments
     * @return
     */
    private static List<Object[]> getIpAddressesByParameters(CommandLineArguments commandLineArguments) {
        print("Fetching requested data from DB ...");

        return logEntryRepository.findByDatePeriodAndCountGreaterThanThreshold(
            commandLineArguments.getStartDate(),
            commandLineArguments.getStartDate().plusHours(commandLineArguments.getDuration().toHours()),
            commandLineArguments.getThreshold()
        );
    }

    private static void print(String message) {
        System.out.println(String.format("\r\n=== %s ===\r\n", message));
    }

}
