// Server implementation
public class TrainServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new TrainServiceImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}

class TrainServiceImpl extends TrainServiceGrpc.TrainServiceImplBase {
    private final Map<String, Receipt> receipts = new ConcurrentHashMap<>();
    private final Map<String, String> userSeatsSectionA = new ConcurrentHashMap<>();
    private final Map<String, String> userSeatsSectionB = new ConcurrentHashMap<>();

    @Override
    public void purchaseTicket(TicketRequest request, StreamObserver<Receipt> responseObserver) {
        String seat;
        if (userSeatsSectionA.size() < userSeatsSectionB.size()) {
            seat = "A" + (userSeatsSectionA.size() + 1);
            userSeatsSectionA.put(request.getUser().getEmail(), seat);
        } else {
            seat = "B" + (userSeatsSectionB.size() + 1);
            userSeatsSectionB.put(request.getUser().getEmail(), seat);
        }

        Receipt receipt = Receipt.newBuilder()
                .setFrom(request.getFrom())
                .setTo(request.getTo())
                .setUser(request.getUser())
                .setPricePaid(20.0f)
                .setSeat(seat)
                .build();

        receipts.put(request.getUser().getEmail(), receipt);

        responseObserver.onNext(receipt);
        responseObserver.onCompleted();
    }

    @Override
    public void getReceiptDetails(ReceiptRequest request, StreamObserver<ReceiptDetails> responseObserver) {
        Receipt receipt = receipts.get(request.getSeat());

        if (receipt != null) {
            responseObserver.onNext(ReceiptDetails.newBuilder().setReceipt(receipt).build());
        } else {
            responseObserver.onNext(ReceiptDetails.newBuilder().build());
        }

        responseObserver.onCompleted();
    }

    @Override
    public void viewUserSeats(SectionRequest request, StreamObserver<UserSeats> responseObserver) {
        Map<String, String> userSeats;
        if (request.getSection().equalsIgnoreCase("A")) {
            userSeats = userSeatsSectionA;
        } else {
            userSeats = userSeatsSectionB;
        }

        UserSeats.Builder userSeatsBuilder = UserSeats.newBuilder();
        for (Map.Entry<String, String> entry : userSeats.entrySet()) {
            userSeatsBuilder.addUserSeats(UserSeat.newBuilder()
                    .setUser(User.newBuilder().setEmail(entry.getKey()).build())
                    .setSeat(entry.getValue())
                    .build());
        }

        responseObserver.onNext(userSeatsBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeUser(UserRequest request, StreamObserver<RemoveUserResponse> responseObserver) {
        String email = request.getEmail();
        if (userSeatsSectionA.containsKey(email)) {
            userSeatsSectionA.remove(email);
        } else if (userSeatsSectionB.containsKey(email)) {
            userSeatsSectionB.remove(email);
        }

        Receipt removedReceipt = receipts.remove(email);

        responseObserver.onNext(RemoveUserResponse.newBuilder().setSuccess(removedReceipt != null).build());
        responseObserver.onCompleted();
    }

    @Override
    public void modifyUserSeat(ModifySeatRequest request, StreamObserver<Receipt> responseObserver) {
        String email = request.getEmail();
        String newSeat = request.getNewSeat();

        if (userSeatsSectionA.containsKey(email)) {
            userSeatsSectionA.put(email, newSeat);
        } else if (userSeatsSectionB.containsKey(email)) {
            userSeatsSectionB.put(email, newSeat);
        }

        Receipt modifiedReceipt = receipts.get(email);
        if (modifiedReceipt != null) {
            modifiedReceipt = modifiedReceipt.toBuilder().setSeat(newSeat).build();
            receipts.put(email, modifiedReceipt);
        }

        responseObserver.onNext(modifiedReceipt);
        responseObserver.onCompleted();
    }
}

