const { app, BrowserWindow, dialog } = require('electron')
require('@electron/remote/main').initialize()

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 1200,
        height: 800,
        useContentSize: true,
        minWidth: 800,
        minHeight: 600,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false
        }
    })
    require("@electron/remote/main").enable(mainWindow.webContents)
    mainWindow.loadFile('index.html')
    // mainWindow.webContents.openDevTools()
    mainWindow.setMenu(null)
    mainWindow.on('closed', function () {
        mainWindow = null
    })
}
app.on('ready', createWindow)