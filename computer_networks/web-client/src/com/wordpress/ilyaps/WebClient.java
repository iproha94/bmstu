package com.wordpress.ilyaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebClient {
    private static int WEB_PORT = 80;

    private String host;
    private int port;

    public String head;
    public StringBuilder body;

    public WebClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private String getStringRequest(String page) {
        StringBuilder query = new StringBuilder("GET ");
        query.append(page);
        query.append(" HTTP/1.1\n");

        query.append("Host: ");
        query.append(this.host);
        query.append(" \n");

        query.append("\n\n");

        return query.toString();
    }

    public boolean loadPage(String page) {
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            s = new Socket(host, port);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        String query = getStringRequest(page);

        out.print(query);
        out.flush();

        StringBuilder headPage = new StringBuilder("");
        StringBuilder bodyPage = new StringBuilder("");

        try {
            String str = in.readLine();

            while (str != null && !str.equals("")) {
                headPage.append("\n").append(str);
                str = in.readLine();
            }

            str = in.readLine();
            while (str != null) {
                bodyPage.append("\n").append(str);
                str = in.readLine();
            }

            in.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.head = headPage.toString();
        this.body = bodyPage;

        return true;
    }

    private boolean checkHead(String[] args) {
        for (String str: args) {
            if (!head.contains(str)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTextPageSuccessLoaded() {
        return checkHead(new String[]{"OK", "200", "text/html"});
    }

    private String bodyConvert() {
        StringBuilder body = this.body;

        String ANSI_RESET = "\u001B[0m";

        String ANSI_TITLE = "\u001B[30;47m";
        String ANSI_P = "\u001B[0;37m";
        String ANSI_A = "\u001B[4;34m";
        String ANSI_H = "\u001B[1;37;40m";
        String ANSI_LI = "\u001B[5;30;47m";
        String ANSI_IMG = "\u001B[31m";

        String ANSI_BLACK = "\u001B[30m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        int pos;
        while ((pos = body.indexOf("<a ")) != -1) {
            int pos2 = body.indexOf(">", pos);
            int pos3 = body.indexOf("</a>", pos);

            String newStr =  " " + ANSI_A + body.substring(pos2 + 1, pos3) + ANSI_RESET + " ";
            body.replace(pos - 1, pos3 + 5, newStr);
        }

        while ((pos = body.indexOf("<img ")) != -1) {
            int pos2 = body.indexOf(">", pos);

            String newStr = ANSI_IMG + "рисунок" + ANSI_RESET;
            body.replace(pos, pos2 + 1, newStr);
        }

        if ((pos = body.indexOf("<body ")) != -1) {
            int pos2 = body.indexOf(">", pos);
            body.delete(pos, pos2 + 1);
        }

        if ((pos = body.indexOf("<head>")) != -1) {
            int pos2 = body.indexOf("</head>", pos);

            body.delete(pos, pos2 + 7);
        }

        if ((pos = body.indexOf("<html>")) != -1) {
            body.delete(pos, pos + 6);
        }

        if ((pos = body.indexOf("</html>")) != -1) {
            body.delete(pos, pos + 7);
        }

        if ((pos = body.indexOf("</body>")) != -1) {
            body.delete(pos, pos + 7);
        }

        while ((pos = body.indexOf("<h1>")) != -1) {
            int pos2 = body.indexOf("</h1>", pos);

            String newStr = ANSI_H + body.substring(pos + 4, pos2) + ANSI_RESET;
            body.replace(pos, pos2 + 5, newStr);
        }

        while ((pos = body.indexOf("<center>\n")) != -1) {
            int pos2 = body.indexOf("</center>", pos);

            body.delete(pos2, pos2 + 9);
            body.replace(pos, pos + 9, "\t\t\t");
        }

        while ((pos = body.indexOf("<center>")) != -1) {
            int pos2 = body.indexOf("</center>", pos);

            body.delete(pos2, pos2 + 9);
            body.replace(pos, pos + 8, "\t\t\t");
        }

        while ((pos = body.indexOf("<p>")) != -1) {
            int pos2 = body.indexOf("</p>", pos);
            int pos3 = body.indexOf("<p>", pos);

            if (pos2 != -1 && pos2 < pos3) {
                body.replace(pos2, pos2 + 4, "\t");
            }

            body.delete(pos, pos + 3);
        }

        while ((pos = body.indexOf("<ul>")) != -1) {
            int pos2 = body.indexOf("</ul>", pos);

            body.delete(pos2, pos2 + 5);
            body.delete(pos, pos + 4);
        }

        while ((pos = body.indexOf("<li>")) != -1) {
            body.replace(pos - 1, pos + 4, "\n" + ANSI_LI + "*)" + ANSI_RESET);
        }

        return body.toString();
    }

    public void printPage() {
        if (!isTextPageSuccessLoaded()) {
            System.out.println("The page is not loaded");
            return;
        }



        System.out.println(this.bodyConvert());
    }



    public static void main(String[] args) {
        String host = "stroustrup.com";
        String page = "/";

        WebClient browser = new WebClient(host, WEB_PORT);
        browser.loadPage(page);
        browser.printPage();
    }
}


