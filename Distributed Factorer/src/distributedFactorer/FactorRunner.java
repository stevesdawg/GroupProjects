package distributedFactorer;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by steve on 1/24/15.
 */
public class FactorRunner {

    public static void main(String[] args) throws IOException {
//        String hostName = "10.0.0.7";
        String hostName = JOptionPane.showInputDialog("Enter Host Name (ip address).");
        Socket socket = new Socket(hostName, 1809);
        FactorerSideNetworkHandler factorNetworker = new FactorerSideNetworkHandler(socket);

        new Thread(factorNetworker).start();
    }

}
