# OutPut

# #----- Purchase Ticket -----

Enter your first name:
Yuvaraj
Enter your last name:
R
Enter your email address:
john.yuvi@example.com
Enter the departure location (e.g., London):
London
Enter the destination location (e.g., France):
France
Purchase successful! Receipt:
from: "London"
to: "France"
user {
  firstName: "Yuvaraj"
  lastName: "R"
  email: "yuvi@example.com"
}
pricePaid: 20.0
seat: "A1"

# #----- Get Receipt Details -----
Enter the seat you want to retrieve details for:
A1
Receipt details:
from: "London"
to: "France"
user {
  firstName: "Yuvaraj"
  lastName: "R"
  email: "yuvi@example.com"
}
pricePaid: 20.0
seat: "A1"

# #----- View User Seats -----
Enter the section you want to view (A or B):
A
Users and seats in Section A:
userSeats {
  user {
    firstName: "Yuvaraj"
    lastName: "R"
    email: "yuvi@example.com"
  }
  seat: "A7"
}

# #----- Remove User -----
Enter the email of the user to be removed:
yuvi@example.com
User with email yuvi@example.com removed successfully.

# #----- Modify User Seat -----
Enter the email of the user to modify:
yuvi@example.com
Enter the new seat for the user:
A2
Seat modification successful! New receipt details:
from: "London"
to: "France"
user {
  firstName: "Yuvaraj"
  lastName: "R"
  email: "yuvi@example.com"
}
pricePaid: 20.0
seat: "A2"
