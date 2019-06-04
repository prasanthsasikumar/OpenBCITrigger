import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

/*
 * Primitive TCP Tagging java client for OpenViBE 1.2.x
 *
 * @author Prasanth Sasikumar & Jussi T. Lindgren / Inria
 * @date 25.Jan.2019
 * @version 0.1
 * @todo Add error handling
 */
class StimulusSender
{
Socket m_clientSocket;
DataOutputStream m_outputStream;

// Open connection to Acquisition Server TCP Tagging
boolean open(String host, Integer port) throws Exception
{
        m_clientSocket = new Socket(host, port);
        m_outputStream = new DataOutputStream(m_clientSocket.getOutputStream());

        return true;
}

// Close connection
boolean close() throws Exception
{
        m_clientSocket.close();

        return true;
}

// Send stimulation with a timestamp.
boolean send(Long stimulation, Long timestamp) throws Exception
{
        ByteBuffer b = ByteBuffer.allocate(24);
        b.order(ByteOrder.LITTLE_ENDIAN); // Assumes AS runs on LE architecture
        b.putLong(0);          // Not used
        b.putLong(stimulation); // Stimulation id
        b.putLong(timestamp);  // Timestamp: 0 = immediate

        m_outputStream.write(b.array());

        return true;
}
}
