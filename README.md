# cse237-project25

Team Members:

* Claire
* Gabriela
* Melena
*

For each iteration you should answer the following:


What user stories were completed this iteration?

In this iteration, we focused on creating login capabilities for our bank customers and for our bank admin - meaning requiring a password to login. We wanted to be able to support multiple bank customer accounts at once within the bank system, which required restructuring our software to more effectively store customer usernames. And to have multiple customers at once, it made sense to allow customers to create passwords and have to login to access their bank account information. We created methods to allow a bank customer to set their password during their user creation, and for the admin, have a general password. We also created a login interface prompting for a password for both the bank admin and the bank customer side of our app.
In order to support multiple bank customers accounts, we also had to change our program to prevent the creation of multiple bank user accounts with the same username. We restructured our interface to reprompt a bank customer user for a different username if they attempt to enter an already used username during account creation. We completed a similar change to prevent the creation of multiple bank accounts under the same bank customer with the same name.

So the bulk of our work for this iteration was focused on the following user stories:
A bank user should be able to set a password for their account.
A bank user should be able to log into their account using their password
A bank admin should have to use a password to log into the bank admin side of the bank app.
A bank user should be remprompted for input if entering a username or bank account name that is invalid or already in use.

Is there anything that you implemented but doesn't currently work?

Sometimes, our scanner gets desynchronized and asks for input twice or requires input twice if the enter key is pressed. We have attempted to fix this problem and have been able to troubleshoot most of these instances, however in testing it still sometimes comes up.

What commands are needed to compile and run your code from the command line (or better yet, provide a script that people can use to run your program!)



To run script (script.sh), after cloning the git repo and cd’ing into the project directory:

chmod +x script.sh ./script.sh

To log into the admin side of the bank app, the password is “admin”.


