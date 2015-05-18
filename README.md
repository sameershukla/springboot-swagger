# springboot-swagger
Sample App to showcase spring boot with swagger with meaningful restful service. 

This service demo doctor appointment system. It contains 3 operations 

1. GET all the appointments of all the doctors in the system 

 http://localhost:8080/epharma/appointment/{status} 
 
 {
  "slot": [
    {
      "id": "38da36b3-5fc8-4808-80a8-57f4f68bc9be",
      "start": "1450",
      "end": "1500",
      "doctor": "sshukla",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/38da36b3-5fc8-4808-80a8-57f4f68bc9be"
      }
    },
    {
      "id": "98fd3164-bcea-45e1-b878-2b86989f9aa4",
      "start": "1550",
      "end": "1600",
      "doctor": "sshukla",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/98fd3164-bcea-45e1-b878-2b86989f9aa4"
      }
    },
    {
      "id": "4bc94d4a-e706-4b91-a65b-ceb7ecb949f9",
      "start": "1750",
      "end": "1500",
      "doctor": "achourey",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/4bc94d4a-e706-4b91-a65b-ceb7ecb949f9"
      }
    },
    {
      "id": "c5b1a536-b133-4049-a8f7-0df8399e3cfe",
      "start": "1650",
      "end": "1500",
      "doctor": "mjones",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/c5b1a536-b133-4049-a8f7-0df8399e3cfe"
      }
    }
  ]
}
 
 In this case status is hardcoded to open
 
2. GET http://localhost:8080/epharma/appointment/{doctorname}/open

Fetch all the open slots of any specific doctor

{
  "slot": [
    {
      "id": "38da36b3-5fc8-4808-80a8-57f4f68bc9be",
      "start": "1450",
      "end": "1500",
      "doctor": "sshukla",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/38da36b3-5fc8-4808-80a8-57f4f68bc9be"
      }
    },
    {
      "id": "98fd3164-bcea-45e1-b878-2b86989f9aa4",
      "start": "1550",
      "end": "1600",
      "doctor": "sshukla",
      "link": {
        "rel": "/slot/book",
        "uri": "/slot/98fd3164-bcea-45e1-b878-2b86989f9aa4"
      }
    }
  ]
}

3. POST http://localhost:8080/epharma/appointment/book/{id}/{patientname}
  
   Pass on slot id from above request and also pass patient name to book an appointment
   
   response 
   
   {
  "isBooked": true,
  "slot": {
    "id": "38da36b3-5fc8-4808-80a8-57f4f68bc9be",
    "start": "1450",
    "end": "1500",
    "doctor": "sshukla",
    "link": null
  }
}

