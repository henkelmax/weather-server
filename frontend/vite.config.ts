import {fileURLToPath, URL} from "node:url";

import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import {VitePWA} from "vite-plugin-pwa";

import gitDescribe from 'git-describe';

export default defineConfig({
    plugins: [
        vue(),
        VitePWA({
            registerType: "prompt",
            devOptions: {
                enabled: true
            },
            workbox: {
                navigateFallbackDenylist: [/^\/api/],
                runtimeCaching: [
                    {
                        urlPattern: ({url}) => url.pathname.startsWith('/api'),
                        handler: 'NetworkOnly'
                    }
                ]
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
                        src: "icons/icon-64x64.png",
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
    },
    define: {
        "__GIT_HASH__": JSON.stringify(gitDescribe.gitDescribeSync().hash)
    }
});
