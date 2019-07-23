import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Input your number");
            String numStr = sc.next();
            Integer num = null;
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                continue;
            }
            if (num < 0) {
                break;
            }
            Requester rq = new Requester(num);
            System.out.println(rq.getStatement());
        }
    }
}
