#!/bin/bash




# 3. Authenticate to get JWT token
echo "Authenticating..."
AUTH_RESPONSE=$(curl -s -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username": "user", "password": "password"}')

# Check for success in authentication
if [[ $? -ne 0 ]]; then
    echo "Authentication failed (check server logs and URL)."
    exit 1
fi

# Extract token from JSON
TOKEN=$(echo "$AUTH_RESPONSE" | jq -r .token)

if [[ "$TOKEN" == "null" || "$TOKEN" == "" ]]; then
    echo "Token not found in response (response was: $AUTH_RESPONSE)."
    exit 1
fi

echo "Authentication successful. Token: $TOKEN"

# 4. Create a new client using the token
echo "Creating client..."
CLIENT_JSON='{
  "firstName": "John",
  "lastName": "Doe",
  "taxIdentifier": "123456789",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "documents": []
}'

CREATION_RESPONSE=$(curl -s -X POST "http://localhost:8080/api/clients" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "$CLIENT_JSON")

# Check for success in client creation
if [[ $? -ne 0 ]]; then
    echo "Client creation failed (token may be invalid)."
    exit 1
fi

# Output results
echo "Client created successfully. Response:"
echo "$CREATION_RESPONSE"

# Optional: Extract and print client ID from response
CLIENT_ID=$(echo "$CREATION_RESPONSE" | jq -r .id)
echo "Client ID: $CLIENT_ID"

# 5. (Optional) List all clients to verify
echo "Listing all clients..."
LIST_RESPONSE=$(curl -s -X GET "http://localhost:8080/api/clients" \
  -H "Authorization: Bearer $TOKEN")

echo "Clients in system:"
echo "$LIST_RESPONSE"