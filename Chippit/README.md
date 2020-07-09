# Programming Project 1 - Groep 6 -- Chippit e-commerce CMS 
For a school project we built an e-commerce CMS platform divided in two parts, the administrator panel and the shop.

The administrator panel is written in Java, we used JavaFX for the GUI and the shop is written in HTML, CSS and JS. We also used NodeJS for the backend. The web part (static pages and node app) are hosted on our Raspberry Pi 3 with an Apache webserver, and we used PM2 to run the node app.

## Java Administrator Panel
The app is composed by several modules:
* Dashboard
* Customer
*	Users
*	Shop
*	Warehouse
*	Marketing
* Finance

When an employee wants to log in, he will need to enter his email and password. If those are correct, he will be invited to enter a two-factor authentication code generated by the Google Authenticator mobile app. Once completed, the system will check the permission of the user. [USERS CODE]. If the user is a warehouse employee, he will not have access to the finance module.

## Used Java libraries
Write & Read Excel & CSV filees
```
ApachePOI
```
Password hashing
```
Bcrypt
```
Send emails
```
jaf-1_1_1_1, javamail-1.4.5
```
Generate QR code
```
Zxing
```
Get FTP connection
```
commons-net-3.6
```
Get a MySQL connection
```
mysql-connector-java-8.0.19
```
Send SMS
```
totp-1.0, twillio
```
## Basic Functionalities
We built some basics functionalities like import and export CSV & Excel files into the system. The user can also create, delete and update data into the MySQL database.

### Dashboard
The dashboard is the main window of the JAVA app. The user will see some important information like orders, customers, …

### Customer
In this module, the user has a table view filled by registered customers of the shop. The user can also sort them by name, date, or email.

### Marketing
The marketing module is a complement to the customer one. In this part of the app, the user can send some campaigns to employees or customers. The user just needs to choose an HTML mail file and the system will send them the selected users. 

For the demo we made two examples. One activation mail and one promotional newsletter.

### Warhouse
Employees with the right permissions can add, remove and update products to the database. The product needs then other information: name, price, quantity, category, keywords, a description, an active Boolean and 3 images paths.

We created a function to copy the local stored image on our FTP server and also crop them automatically to the correct size of 533x533.
After that, the system creates a Qr-code who redirects the user to the product page on the internet page.

### Finance
In this module, an employee could generate an invoice. In order to do, the system create another QR code that redirects the user to a page on our server. This page takes the order ID and looks for ordered product in the data base.

### Users
The shop owner can also add new employees for his shop. As for products he needs to put the employee’s name, birthdate, permissions. The system will generate a default password and a TFA Google code for.