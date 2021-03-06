module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: true
    }
  },
  pwa: {
    name: "Weather",
    themeColor: "#121212",
    workboxOptions: {
      skipWaiting: true
    }
  }
}
