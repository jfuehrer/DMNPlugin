#!/bin/bash
# Script to start both the SimpleDMNPluginStub and GitHub Push UI Server
# This shows all capabilities of the DMN plugin without requiring Magic Systems installation

# Color codes for better readability
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}DMN Plugin Complete Demo Starter${NC}"
echo "================================="
echo

echo -e "${YELLOW}Step 1: Building DMN Plugin Stub${NC}"
echo "----------------------------"

# Run the build script for the stub version
if [ -f "./build_stub_version.sh" ]; then
    chmod +x ./build_stub_version.sh
    ./build_stub_version.sh
    
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed. Cannot run demo.${NC}"
        exit 1
    fi
else
    echo -e "${RED}Error: build_stub_version.sh not found${NC}"
    exit 1
fi

echo -e "\n${YELLOW}Step 2: Starting GitHub Push UI Server${NC}"
echo "----------------------------"
echo -e "${GREEN}Starting GitHub Push UI Server in the background...${NC}"

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo -e "${RED}Error: Node.js is not installed${NC}"
    echo "Please install Node.js to run the GitHub Push UI Server"
    exit 1
fi

# Start the GitHub Push UI Server in the background
node server.js &
SERVER_PID=$!

# Wait for the server to start
sleep 2

echo -e "${GREEN}GitHub Push UI Server running at http://localhost:5000${NC}"
echo -e "${GREEN}Use Ctrl+C to stop all services when done${NC}"
echo

echo -e "\n${YELLOW}Step 3: Running DMN Plugin Demo${NC}"
echo "----------------------------"
echo -e "${GREEN}Starting SimpleDMNPluginStub demonstration...${NC}"
echo

# Run the SimpleDMNPluginStub demo
java -cp build:lib/* com.example.dmn.plugin.SimpleDMNPluginStub

echo
echo -e "${BLUE}SimpleDMNPluginStub Demo Complete${NC}"
echo "=============================="
echo
echo -e "${GREEN}GitHub Push UI Server is still running at http://localhost:5000${NC}"
echo "Press Ctrl+C to stop the server when you're done"

# Wait for user to terminate
wait $SERVER_PID