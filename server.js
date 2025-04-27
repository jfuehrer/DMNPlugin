const express = require('express');
const { exec } = require('child_process');
const path = require('path');
const fs = require('fs');

const app = express();
const PORT = 5000;

// Middleware to parse JSON bodies
app.use(express.json());

// Serve static files (HTML, CSS, JS)
app.use(express.static('./'));

// API endpoint to run scripts
app.post('/run-script', (req, res) => {
    const { script } = req.body;
    
    // Validate script parameter
    if (!script) {
        return res.status(400).json({ success: false, output: 'No script specified.' });
    }
    
    // Security check - only allow our specific scripts
    const allowedScripts = ['./github_push.sh'];
    if (!allowedScripts.includes(script)) {
        return res.status(403).json({ success: false, output: 'Script not allowed.' });
    }

    // Check if script exists
    if (!fs.existsSync(script)) {
        return res.status(404).json({ success: false, output: `Script ${script} not found.` });
    }
    
    // Execute the script
    exec(`chmod +x ${script} && ${script}`, (error, stdout, stderr) => {
        if (error) {
            return res.json({ 
                success: false, 
                output: `${stdout}\n\nError: ${stderr}`
            });
        }
        
        return res.json({ 
            success: true, 
            output: stdout 
        });
    });
});

// Default route to serve the HTML interface
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'github-push-ui.html'));
});

// Start server
app.listen(PORT, '0.0.0.0', () => {
    console.log(`GitHub Push Server running on port ${PORT}`);
    console.log(`Open http://localhost:${PORT} in your browser`);
});