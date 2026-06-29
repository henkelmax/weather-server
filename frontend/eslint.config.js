import vue from 'eslint-plugin-vue'
import vuetify from 'eslint-plugin-vuetify'
import ts from 'typescript-eslint'

export default [
    ...vue.configs['flat/base'],
    ...vuetify.configs['flat/recommended-v4'],
    {
        files: ['**/*.vue'],
        languageOptions: {
            parserOptions: {
                parser: ts.parser,
                sourceType: 'module'
            }
        }
    }
]