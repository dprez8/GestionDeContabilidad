const path = require('path');
const outputDirectory = path.resolve(__dirname, 'src', 'main', 'resources', 'public');
const contentBaseDir = path.resolve(__dirname, 'src', 'main', 'resources', 'assets');
const entryFile = path.resolve(__dirname, 'src', 'main','javascript', 'main.js');
const indexHtmlTemplate = path.resolve(__dirname, 'src', 'main','resources', 'templates', 'index.html');

module.exports = {
    publicPath: '/',
    outputDir: outputDirectory,
    assetsDir: '',
    devServer: {
        contentBase: contentBaseDir,
        compress: true,
        port: 9000,
        disableHostCheck: true,
        proxy: {
            '^/*': {
                target: 'http://localhost:80',
                changeOrigin: true
            },
        }
    },
    pages: {
        index: {
            // entry for the page
            entry: entryFile,
            // the source template
            template: indexHtmlTemplate,
            // output as dist/index.html
            filename: 'index.html',
            // when using title option,
            // template title tag needs to be <title><%= htmlWebpackPlugin.options.title %></title>
            title: 'GeSoc',
            // chunks to include on this page, by default includes
            // extracted common chunks and vendor chunks.
            chunks: ['chunk-vendors', 'chunk-common', 'index']
        }
    }
}