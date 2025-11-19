const electron = require("electron");
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;
const Menu = electron.Menu;

function createWindow() {
    const mainWindow = new BrowserWindow({
    width: 300,
    height: 450,
    webPreferences: {
    nodeIntegration: true,
    contextIsolation: false

    }
    })

    mainWindow.loadFile('index.html')

    // Open the DevTools.
    mainWindow.webContents.openDevTools()
}

app.on('ready',createWindow);