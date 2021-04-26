const fs = require('fs')

ENVIRONMENT_DIRECTORY = "./src/environments"
VALID_ENVIRONMENTS = ["dev", "prod"]

function buildProdEnvObject() {
    let environment = {
        production: true,
        email_user: process.env['EMAIL_USER'] || "",
        email_pass: process.env['EMAIL_PASS'] || "",
        api_base: process.env['API_BASE'] || ""
    }
    return environment
}

function buildDevEnvObject() {
    require('dotenv').config()
    let environment = {
        production: false,
        email_user: process.env.user || "",
        email_pass: process.env.pass || "",
        api_base: "http://localhost:8081"
    }
    return environment
}

function buildEnvFile(envObject) {
    return `export const environment = ${JSON.stringify(envObject)}`
}

var envFileContent;
var fileName;

if (process.argv.length <= 2 || !VALID_ENVIRONMENTS.includes(process.argv[2])) {
    console.log("Specify prod or dev environment")
    return
}

if (process.argv[2] == "prod") {
    console.log("Generating prod environment.ts")
    envFileContent = buildEnvFile(buildProdEnvObject())
    fileName = "environment.prod.ts"
}

else if (process.argv[2] == "dev") {
    console.log("Generating dev environment.ts")
    envFileContent = buildEnvFile(buildDevEnvObject())
    fileName = "environment.ts"
}

fs.writeFile(`${ENVIRONMENT_DIRECTORY}/${fileName}`, envFileContent, err => {
    if (err) throw err
    console.log("Generated env file")
})