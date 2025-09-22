I had no problems with installation of Java Persistence Architecture (JPA)

For this assignment I found it very hard to inspect the database in DBeaver, because of different versions. I tried to downgrade my applications version but was unsuccsessful, and I tried to upgrade DBeaver´s version but was also unsuccesful.
However I was able to print created tables in the terminal:

Table: users
ID EMAIL HASACCESS ISLOGGEDIN PASSWORD USERNAME
1 bob@example.com FALSE FALSE password bob
2 alice@example.com FALSE FALSE 1234 alice

Table: polls
ID ISPRIVATE PUBLISHEDAT QUESTION VALIDUNTIL CREATEDBY_ID
1 FALSE 2025-09-21 18:06:35.931687+00 What is your favorite programming language? 2025-09-22 18:06:35.93169+00 1
2 FALSE 2025-09-22 09:48:04.920681+00 What is your favorite programming language? 2025-09-23 09:48:04.92068+00 3

Table: vote_options
ID CAPTION PRESENTATIONORDER POLL_ID
1 Java 0 1
2 Python 0 1
3 JavaScript 0 1

Table: votes
ID ISVALID PUBLISHEDAT VOTER_ID
1 FALSE 2025-09-21 18:06:35.931708+00 1
2 FALSE 2025-09-21 18:06:35.931713+00 2
