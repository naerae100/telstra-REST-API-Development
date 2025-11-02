# Test Commands

## 1. Activate a SIM Card
```bash
curl -X POST http://localhost:8080/activate \
  -H "Content-Type: application/json" \
  -d '{"iccid":"1234567890","customerEmail":"test@example.com"}'
```

## 2. Query by ID (note the quotes around the URL!)
```bash
curl "http://localhost:8080/query?simCardId=1"
```

## Important Notes:
- In zsh, you MUST put quotes around URLs with query parameters
- The simCardId corresponds to the database ID (usually starts at 1)
- After activating a SIM card, you'll see it saved with an auto-generated ID

