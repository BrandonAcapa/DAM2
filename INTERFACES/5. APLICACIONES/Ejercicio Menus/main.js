const {app, BrowserWindow, Menu, shell, MenuItem, Tray} = require("electron");

let mainWindow;

function createWindow(file = 'index.html', setAsMain = true) {
  const win = new BrowserWindow({
    width: 300,
    height: 450,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false
    }
  });

  win.loadFile(file);

  // Open the DevTools.
  // win.webContents.openDevTools()

  if (setAsMain) {
    mainWindow = win;
  }

  return win;
}

app.on('ready', function () {
  createWindow();
  const template = [
    { label: 'Edit' },
    { label: 'Demo',
      submenu: [
        {
          label: 'Cerrar',
          click: function () {
            mainWindow.close();
          }
        },
        { type: 'separator' },
        {
          label: 'Maximizar',
          click: function () {
            mainWindow.maximize();
          }
        },
        { type: 'separator' },
        {
            label: 'Abrir ventana',
            click: function () {
              createWindow('index2.html', false);
            }
        }
    ]},
    { label: 'Help',
      submenu: [
        {
            label: 'About Electron',
            click: function (){
                shell.openExternal('http://electron.atom.io');
            },
            accelerator: 'CmdOrCtrl + Shift + H'
        }
    ]}
]

    const ctxMenu = Menu.buildFromTemplate([
    {
      label: 'Hello',
      click: function () {
      console.log('ctx menu clicked')
      }
    },
    { role: 'copy' },
    { role: 'cut' },
    { role: 'paste' },
    { role: 'selectall' }
    ])

    mainWindow.webContents.on('context-menu', (e, params) => {
        ctxMenu.popup(mainWindow, params.x, params.y);
    })
    const menu = Menu.buildFromTemplate(template);
    Menu.setApplicationMenu(menu);

    let TrayIcon = new Tray('tray.png');
    const contextMenu = Menu.buildFromTemplate([
        { label: 'Item1', type: 'radio' },
        { label: 'Item2', type: 'radio' }
    ])

    TrayIcon.setContextMenu(contextMenu);
});