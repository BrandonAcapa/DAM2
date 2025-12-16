const {app, BrowserWindow} = require('electron');

require('@electron/remote/main').initialize();

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 680,
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

    require('@electron/remote/main').enable(mainWindow.webContents);
}

app.on('ready', createWindow);

//npm install @electron/remote