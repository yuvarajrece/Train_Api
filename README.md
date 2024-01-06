# Train_Api


If you can run the code in your local development environment. Here's a brief explanation of what you can expect when running the provided code:

1. **Purchase Ticket:**
   - The program will prompt you to enter your details (first name, last name, email), departure location, and destination location.
   - After entering the information, it will make a gRPC call to the server to purchase a ticket.
   - The response will be printed, showing the details of the purchased ticket.

2. **Get Receipt Details:**
   - The program will prompt you to enter the seat for which you want to retrieve details.
   - After entering the seat, it will make a gRPC call to the server to get the receipt details for the specified seat.
   - The response will be printed, either showing the receipt details or indicating that no receipt was found for the provided seat.

3. **View User Seats:**
   - The program will prompt you to enter the section (A or B) for which you want to view user seats.
   - After entering the section, it will make a gRPC call to the server to get the list of users and their seats in the specified section.
   - The response will be printed, either showing the user seats or indicating that no users were found in the specified section.

4. **Remove User:**
   - The program will prompt you to enter the email of the user you want to remove.
   - After entering the email, it will make a gRPC call to the server to remove the user.
   - The response will be printed, indicating whether the removal was successful or if no user was found with the provided email.

5. **Modify User Seat:**
   - The program will prompt you to enter the email of the user whose seat you want to modify and the new seat.
   - After entering the information, it will make a gRPC call to the server to modify the user's seat.
   - The response will be printed, showing the details of the modified receipt or indicating that no user was found with the provided email for seat modification.

Run the `TrainServer` class first to start the gRPC server, and then run the `TrainClient` class to interact with the server. Adjust the prompts and input as needed during the execution of the client application.


**Generate Java code from the protocol buffer file using the protoc compiler:**


protoc --plugin=protoc-gen-grpc-java=/path/to/protoc-gen-grpc-java --java_out=src --grpc-java_out=src TrainService.proto



Make sure to replace /path/to/protoc-gen-grpc-java with the actual path to the protoc-gen-grpc-java plugin on your machine.

