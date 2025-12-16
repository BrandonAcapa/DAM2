const {app, BrowserWindow, ipcMain, Tray, Menu} = require("electron");
// const app = electron.app;
// const BrowserWindow = electron.BrowserWindow;
process.env['ELECTRON_DISABLE_SECURITY_WARNINGS'] = 'true';
const path = require("path");

function createWindow() {
    const mainWindow = new BrowserWindow({

    webPreferences: {
    nodeIntegration: true,
    contextIsolation: false,
    show: false

    }
    })

    mainWindow.loadFile('index.html')
    // Open the DevTools.
    // mainWindow.webContents.openDevTools()
}

app.on('ready', function(){
    createWindow();
    const iconPath = path.join(__dirname, 'assets/camera.ico');
    const tray = new Tray(iconPath);
    const menuTemplate = [
        {
            label: "Quit",
            click: () => {
                app.quit();
            }
        }
    ];
});