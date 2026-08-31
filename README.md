# 💬 Local Wi-Fi Chat & Direct Messaging Web App

A lightweight, real-time messaging web application built with **Java (Spring Boot)** and **WebSockets**.

The application allows devices connected to the **same local Wi-Fi network (LAN)** to discover each other, join a shared public chatroom, and send direct private messages.

---

## 🚀 Features

* **⚡ Real-Time WebSocket Communication**
  Instant, bidirectional messaging without refreshing the page.

* **📡 Local Network Access**
  Run the application on one PC and allow other devices on the same Wi-Fi network to connect using the host PC's local IP address.

* **👥 Active User Discovery**
  See a live list of users currently connected to the application.

* **💬 Direct Messaging (DMs)**
  Send private messages to individual users through dedicated conversation tabs.

* **📝 In-Memory Chat History**
  Public chat history is preserved while the server is running and can be provided to newly connected users.

* **💾 Local Client State**
  Conversation/tab state can be maintained locally in the browser.

* **🌐 No Client Installation Required**
  Works directly in modern web browsers, including:

  * Google Chrome
  * Microsoft Edge
  * Safari
  * Mobile browsers

---

## 🛠️ Tech Stack

| Component        | Technology                   |
| ---------------- | ---------------------------- |
| Backend          | Java 17+                     |
| Framework        | Spring Boot 3.x              |
| Communication    | Spring WebSocket             |
| Frontend         | HTML5, CSS3, JavaScript      |
| WebSocket Client | Native Browser WebSocket API |
| Data Format      | JSON                         |
| Build Tool       | Apache Maven                 |

---

## 📂 Project Structure

```text
untitled/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── org/
        │       └── example/
        │           ├── Application.java
        │           ├── ChatHandler.java
        │           ├── ChatMessage.java
        │           └── WebSocketConfig.java
        │
        └── resources/
            └── static/
                └── index.html
```

---

# 📋 Setup & Installation

## 1. Prerequisites

Before running the application, make sure you have:

* **JDK 17 or higher**
* **IntelliJ IDEA** or another Java IDE
* **Apache Maven** (if not using the Maven wrapper)
* Devices connected to the **same Wi-Fi / Local Area Network (LAN)**

---

## 2. Open the Project in IntelliJ IDEA

1. Clone or copy the project into your IntelliJ workspace.

2. Make sure `pom.xml` is located in the **project root directory**, outside of `src`.

3. Open the project in IntelliJ IDEA.

4. Allow IntelliJ to detect the Maven project.

5. Click the **Maven Refresh / Reload** button to download and load the required dependencies, including:

   * `spring-boot-starter-web`
   * `spring-boot-starter-websocket`

---

# 🏃 Running the Application

### Using IntelliJ IDEA

1. Open:

   ```text
   src/main/java/org/example/Application.java
   ```

2. Find the `Application` class.

3. Click the green **▶ Run** button next to:

   ```java
   public class Application
   ```

4. Select:

   ```text
   Run 'Application.main()'
   ```

5. The Spring Boot server should start on:

   ```text
   http://localhost:8080
   ```

### Verify the Server

Open a browser on the host PC and visit:

```text
http://localhost:8080
```

If the application loads successfully, the server is running.

---

# 📱 Connecting Other Devices Over Wi-Fi

Other devices can connect to the application as long as they are connected to the **same local network** as the host PC.

## 1. Find the Host PC's Local IP Address

### Windows

Open **Command Prompt** and run:

```cmd
ipconfig
```

Look for the **IPv4 Address** of your active Wi-Fi adapter.

Example:

```text
IPv4 Address. . . . . . . . . . . : 192.168.1.15
```

### macOS

Open Terminal and run:

```bash
ipconfig getifaddr en0
```

If that doesn't work, you can use:

```bash
ifconfig
```

Look for the local IP address associated with your active network interface.

### Linux

You can use:

```bash
ip addr
```

or:

```bash
hostname -I
```

Look for an address similar to:

```text
192.168.1.15
```

---

## 2. Connect From Another Device

On the host PC:

```text
http://localhost:8080
```

On a phone, tablet, laptop, or another computer connected to the same Wi-Fi:

```text
http://<HOST-LOCAL-IP>:8080
```

For example:

```text
http://192.168.1.15:8080
```

> **Important:** Replace `192.168.1.15` with the actual local IP address of the computer running the Spring Boot server.

---

# 🛡️ Firewall & Network Troubleshooting

If other devices cannot access the application, check the following.

## Windows Firewall

Windows Firewall may block incoming connections to port `8080`.

### Create an Inbound Rule

1. Open **Windows Defender Firewall with Advanced Security**.

2. Select:

   ```text
   Inbound Rules
   ```

3. Click:

   ```text
   New Rule...
   ```

4. Select:

   ```text
   Port
   ```

5. Select:

   ```text
   TCP
   ```

6. Enter:

   ```text
   Specific local ports: 8080
   ```

7. Select:

   ```text
   Allow the connection
   ```

8. Select the appropriate network profiles.

9. Give the rule a name such as:

   ```text
   Spring Boot Port 8080
   ```

---

## 🌐 Check Your Network Profile

On Windows, make sure your active Wi-Fi network is configured as **Private** if appropriate for your trusted local network.

A **Public** network profile may apply stricter firewall rules and prevent other devices from connecting.

---

## 🔍 If the Page Still Doesn't Load

Check the following:

* ✅ Both devices are connected to the **same Wi-Fi network**.
* ✅ The Spring Boot application is running.
* ✅ The server is listening on port `8080`.
* ✅ You are using the host PC's **local IPv4 address**, not `localhost`.
* ✅ Windows Firewall allows TCP connections on port `8080`.
* ✅ Your router does not have **client/AP isolation** enabled.
* ✅ The host PC is not connected to a VPN that changes or isolates network traffic.
* ✅ The device can reach the host PC on the local network.

### Example

If the host PC has:

```text
IPv4 Address: 192.168.1.15
```

Then another device should use:

```text
http://192.168.1.15:8080
```

**Do not use:**

```text
http://localhost:8080
```

on the other device.

`localhost` always refers to **the device you are currently using**, not the computer hosting the application.

---

# 🔌 WebSocket Connectivity

The application uses WebSockets for real-time communication.

If the web page loads but messages are not being sent or received:

1. Confirm that the devices are on the same LAN.
2. Check the browser's developer console for WebSocket errors.
3. Check the Spring Boot console for server-side errors.
4. Verify that port `8080` is allowed through the firewall.
5. Check whether your router uses wireless client isolation.

---

# 🔐 Security Note

This application is designed for **local network use**.

It should **not be exposed directly to the public internet** without adding appropriate security measures such as:

* Authentication
* Authorization
* HTTPS/WSS
* Input validation
* Rate limiting
* Secure session management
* Proper message privacy controls

Messages transmitted through this application should not be considered secure merely because they are sent over a local network.

---

# 📄 License

This project is open-source and available under the **MIT License**.

See the [`LICENSE`](LICENSE) file for the full license text.

---

# 🤝 Contributing

Contributions, bug reports, and feature requests are welcome.

If you'd like to contribute:

1. Fork the repository.

2. Create a new branch:

   ```bash
   git checkout -b feature/my-new-feature
   ```

3. Make your changes.

4. Commit your changes:

   ```bash
   git commit -m "Add new feature"
   ```

5. Push the branch:

   ```bash
   git push origin feature/my-new-feature
   ```

6. Open a Pull Request.

---

## ⭐ Getting Started Quickly

For a quick summary:

```text
1. Install JDK 17+
        ↓
2. Open the project in IntelliJ
        ↓
3. Refresh Maven dependencies
        ↓
4. Run Application.java
        ↓
5. Server starts on port 8080
        ↓
6. Find the host PC's local IP
        ↓
7. Connect other devices using:
   http://<HOST-IP>:8080
```

**Example:**

```text
http://192.168.1.15:8080
```
