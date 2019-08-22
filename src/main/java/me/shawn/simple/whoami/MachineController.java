package me.shawn.simple.whoami;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MachineController {

    @GetMapping("/network")
    public Map<String, Object> getIp() throws UnknownHostException, SocketException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostName = inetAddress.getHostName();
        String hostAddress = inetAddress.getHostAddress();
        byte[] address = inetAddress.getAddress();
        String canonicalHostName = inetAddress.getCanonicalHostName();

        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
        byte[] hardwareAddress = networkInterface.getHardwareAddress();
        String displayName = networkInterface.getDisplayName();
        String name = networkInterface.getName();

        Map<String, Object> response = new HashMap<>();
        response.put("hostName", hostName);
        response.put("hostAddress", hostAddress);
        response.put("address", address);
        response.put("canonicalHostName", canonicalHostName);
        response.put("hardwareAddress", hardwareAddress);
        response.put("macAddress", convertMacAddressToString(hardwareAddress));
        response.put("displayName", displayName);
        response.put("networkName", name);

        return response;
    }

    private String convertMacAddressToString(byte[] hardwareAddress) {
        StringBuilder sb = new StringBuilder();
        for(byte b : hardwareAddress) {
            if(sb.length() > 0) sb.append(":");
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    @GetMapping("/os")
    public OperatingSystemMXBean getOperatingSystemInfo() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        return operatingSystemMXBean;
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "world") String name) {
        return String.format("Hello, %s!", name);
    }

}
