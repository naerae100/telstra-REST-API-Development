# Quick Start Guide

## Step 1: Start the Actuator Service (Required First!)

Open a terminal and run:
```bash
cd /Users/zoro/Downloads/telstra-REST-API-Development-main
java -jar services/SimCardActuator.jar
```

**Keep this terminal open** - the actuator must stay running!

You should see the actuator service start. It will listen on port 8444.

## Step 2: Start Your Spring Boot Application

Open a **NEW terminal window** (keep the actuator running in the first one) and run:
```bash
cd /Users/zoro/Downloads/telstra-REST-API-Development-main
./mvnw spring-boot:run
```

Wait until you see: `Started SimCardActivator` in the console.

## Step 3: Test It!

Open yet another terminal and test your endpoint:
```bash
curl -X POST http://localhost:8080/activate \
  -H "Content-Type: application/json" \
  -d '{"iccid":"1234567890","customerEmail":"test@example.com"}'
```

You should see:
- Response: `SIM card activated successfully` or `SIM card activation failed`
- In the Spring Boot console: Debug messages showing the actuator call

## That's It! 🎉

Your microservice is now running and ready to process SIM card activation requests!

