import {fileURLToPath, URL} from "node:url"

import {defineConfig} from "vite"
import vue from "@vitejs/plugin-vue"
import {VitePWA} from "vite-plugin-pwa"

export default defineConfig({
    plugins: [
        vue(),
        VitePWA({
            registerType: "autoUpdate",
            devOptions: {
                enabled: true
            },
            manifest: {
                name: "Weather",
                short_name: "Weather",
                description: "Weather",
                theme_color: "#121212",
                icons: [
                    {
                        src: "icons/icon-512x512.png",
                        sizes: "512x512",
                        type: "image/png"
                    },
                    {
                        src: "icons/icon-512x512.png",
                        sizes: "512x512",
                        type: "image/png",
                        purpose: "maskable"
                    },
                    {
                        src: "icons/icon-192x192.png",
                        sizes: "192x192",
                        type: "image/png"
                    },
                    {
                        src: "icons/icon-64x64",
                        sizes: "64x64",
                        type: "image/png"
                    }
                ]
            }
        })
    ],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url))
        }
    }
})
