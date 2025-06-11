package com.easyfrp.util;

import com.easyfrp.model.FrpProxy;
import lombok.SneakyThrows;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrpConfigReader {

    private static final Pattern SECTION_PATTERN = Pattern.compile("^\\[(.+)]$");

    @SneakyThrows
    public static List<FrpProxy> parseFrpConfig(File file) {
        List<FrpProxy> proxies = new ArrayList<>();
        Scanner scanner = new Scanner(file);

        FrpProxy current = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty() || line.startsWith("#") || line.startsWith(";")) continue;

            Matcher matcher = SECTION_PATTERN.matcher(line);
            if (matcher.matches()) {
                if (!"common".equalsIgnoreCase(matcher.group(1))) {
                    current = new FrpProxy();
                    current.setName(matcher.group(1));
                    proxies.add(current);
                } else {
                    current = null;
                }
            } else if (current != null && line.contains("=")) {
                String[] parts = line.split("=", 2);
                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "type":
                        current.setType(value);
                        break;
                    case "local_ip":
                        current.setLocalIp(value);
                        break;
                    case "local_port":
                        current.setLocalPort(Integer.parseInt(value));
                        break;
                    case "remote_port":
                        current.setRemotePort(Integer.parseInt(value));
                        break;
                    case "custom_domains":
                        current.setCustomDomains(value);
                        break;
                }
            }
        }

        scanner.close();
        return proxies;
    }
}
