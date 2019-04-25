# How to run

- #### run `mvn clean install` to build

- #### Run for development :

  - ##### For super dev mode 
  
    - In one terminal run `mvn gwt:codeserver -pl *-frontend -am`
    
    - In another terminal `cd wallet-backend`
    - execute `mvn exec:java`
    - the server port will be printed in the logs access the application on `http://localhost:[port]`

  - ##### For gwt compiled mode 
  
    - `cd wallet-backend`
    - execute `mvn exec:java -Dmode=compiled`
    - the server port will be printed in the logs access the application on `http://localhost:[port]`

  - ##### For production mode 
  
    - `cd wallet-backend`
    - execute `java -jar target/wallet-backend-1.0-SNAPSHOT-fat.jar`
    - the server port will be printed in the logs access the application on `http://localhost:[port]`
