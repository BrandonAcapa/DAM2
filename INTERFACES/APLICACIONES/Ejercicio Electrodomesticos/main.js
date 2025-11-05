const {app, BrowserWindow} = require('electron');

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 500,
        height: 510,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false
        }
    })

    mainWindow.loadFile('index.html');
    mainWindow.on('closed', function (){
        mainWindow = null;
    })
}

app.on('ready', createWindow);