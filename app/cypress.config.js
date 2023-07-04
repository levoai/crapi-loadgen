const { defineConfig } = require("cypress");

module.exports = defineConfig({
  env:
  {
    site:'http://crapi-dev.levo.ai/'
  },
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
      on('before:browser:launch', (browser = {}, launchOptions) => {
        if (browser.family === 'chromium' && browser.name !== 'electron') {
          launchOptions.args.push('--start-fullscreen')
      
          return launchOptions
        }
      
        if (browser.name === 'electron') {
          launchOptions.preferences.fullscreen = true
      
          return launchOptions
        }
      })
    },
    specPattern:'cypress/integration/ui/*.js'
  },
});
