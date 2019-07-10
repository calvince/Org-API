# Organisational Api.
:satisfied:
#### An API App that offers access to news to a department and general news in an organisation.  , Wednesday july 2019
#### By **OMONGE CALVINCE**&trade;
## Description

Organisational Api gives you the ability to query for all the users and the departments associated with them.


## Project setup instructions
* Make sure you have all the Requirements of running Java apps installed such as JUnit, intellij, SDK, JDK.

* Clone the project into your machine from https://github.com/calvince/Org-API .

* Gradle run javac Org-API.java to compile and java OrgaAPI to run the program


## Technologies used
* Java.
* Gradle(for unit testing).
* Bootstrap.
* Spark.
* Material Design Bootstrap.

### Installation
1. Clone the repo `https://github.com/calvince/Org-API.git`
2. CD into the folder `cd Org-API`

## Behavior Driven Development
 | Behavior : | Input:     | Output: |
 | :------------- | :------------- | :-------------         |
 | Add news       | Input Data       | News is added to the news table    |
 | Add a department       | Input department data       | The department info is added to the table of departments  |
 | Add a Employee       | Input Employee data in json       | Employee added to Employees Table    |
 | Retrieve all departments a user belongs to | Make a get request on `employees/1/department` where '1' is the EmployeeId | All departments that an employee belongs to are displayed |

## Future release
Make the APi available for Use

## Support and contact details
contact me @ calmosh1421@gmail.com

### License
The project is under[MIT license](https://github.com/calvince/Org-API/blob/master/LICENSE)
Copyright &copy; 2019.All right reserved
