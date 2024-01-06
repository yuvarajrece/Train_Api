//updated Client Implementation 


import java.util.Scanner;

public class TrainClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        TrainServiceGrpc.TrainServiceBlockingStub blockingStub = TrainServiceGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);

        // Example usage of the APIs with user input
        purchaseTicket(blockingStub, scanner);
        getReceiptDetails(blockingStub, scanner);
        viewUserSeats(blockingStub, scanner);
        removeUser(blockingStub, scanner);
        modifyUserSeat(blockingStub, scanner);

        channel.shutdown();
        scanner.close();
    }

    private static void purchaseTicket(TrainServiceGrpc.TrainServiceBlockingStub blockingStub, Scanner scanner) {
        System.out.println("----- Purchase Ticket -----");
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter your email address:");
        String email = scanner.nextLine();

        System.out.println("Enter the departure location (e.g., London):");
        String from = scanner.nextLine();

        System.out.println("Enter the destination location (e.g., France):");
        String to = scanner.nextLine();

        User user = User.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .build();

        TicketRequest request = TicketRequest.newBuilder()
                .setFrom(from)
                .setTo(to)
                .setUser(user)
                .build();

        Receipt response = blockingStub.purchaseTicket(request);
        System.out.println("Purchase successful! Receipt:\n" + response);
    }

    private static void getReceiptDetails(TrainServiceGrpc.TrainServiceBlockingStub blockingStub, Scanner scanner) {
        System.out.println("----- Get Receipt Details -----");
        System.out.println("Enter the seat you want to retrieve details for:");
        String seat = scanner.nextLine();

        ReceiptRequest request = ReceiptRequest.newBuilder()
                .setSeat(seat)
                .build();

        ReceiptDetails response = blockingStub.getReceiptDetails(request);

        if (response.hasReceipt()) {
            System.out.println("Receipt details:\n" + response.getReceipt());
        } else {
            System.out.println("No receipt found for the provided seat.");
        }
    }

    private static void viewUserSeats(TrainServiceGrpc.TrainServiceBlockingStub blockingStub, Scanner scanner) {
        System.out.println("----- View User Seats -----");
        System.out.println("Enter the section you want to view (A or B):");
        String section = scanner.nextLine();

        SectionRequest request = SectionRequest.newBuilder()
                .setSection(section)
                .build();

        UserSeats response = blockingStub.viewUserSeats(request);

        if (response.getUserSeatsCount() > 0) {
            System.out.println("Users and seats in Section " + section + ":\n" + response);
        } else {
            System.out.println("No users found in Section " + section + ".");
        }
    }

    private static void removeUser(TrainServiceGrpc.TrainServiceBlockingStub blockingStub, Scanner scanner) {
        System.out.println("----- Remove User -----");
        System.out.println("Enter the email of the user to be removed:");
        String email = scanner.nextLine();

        UserRequest request = UserRequest.newBuilder()
                .setEmail(email)
                .build();

        RemoveUserResponse response = blockingStub.removeUser(request);

        if (response.getSuccess()) {
            System.out.println("User with email " + email + " removed successfully.");
        } else {
            System.out.println("No user found with the provided email.");
        }
    }

    private static void modifyUserSeat(TrainServiceGrpc.TrainServiceBlockingStub blockingStub, Scanner scanner) {
        System.out.println("----- Modify User Seat -----");
        System.out.println("Enter the email of the user to modify:");
        String email = scanner.nextLine();

        System.out.println("Enter the new seat for the user:");
        String newSeat = scanner.nextLine();

        ModifySeatRequest request = ModifySeatRequest.newBuilder()
                .setEmail(email)
                .setNewSeat(newSeat)
                .build();

        Receipt response = blockingStub.modifyUserSeat(request);

        if (response.hasSeat()) {
            System.out.println("Seat modification successful! New receipt details:\n" + response);
        } else {
            System.out.println("No user found with the provided email for seat modification.");
        }
    }
}
