# Testing Instructions

## Step 1: Start the Actuator Service
In a terminal, run:
```bash
java -jar services/SimCardActuator.jar
```
You should see output indicating the service started on port 8444.

## Step 2: Test the Actuator Directly
In another terminal, test if the actuator is working:
```bash
curl -X POST http://localhost:8444/actuate \
  -H "Content-Type: application/json" \
  -d '{"iccid":"1234567890"}'
```

Expected response:
```json
{"success": true}
```
or
```json
{"success": false}
```

## Step 3: Start Your Spring Boot Application
```bash
./mvnw spring-boot:run
```
Wait for it to start (look for "Started SimCardActivator").

## Step 4: Test Your Endpoint
In another terminal:
```bash
curl -X POST http://localhost:8080/activate \
  -H "Content-Type: application/json" \
  -d '{"iccid":"1234567890","customerEmail":"test@example.com"}'
```

## Check the Console Output
Your application will print:
- "Calling actuator service at: http://localhost:8444/actuate"
- "Request payload: {"iccid": "1234567890"}"
- "Actuator response: {"success": true}" (or false)
- "SIM card activation successful for ICCID: ..." (or failed)

## Common Issues:
1. **Connection refused** - Actuator service not running
2. **Connection timeout** - Wrong port or firewall issue
3. **404 Not Found** - Wrong URL endpoint
4. **500 Internal Server Error** - Check actuator logs

