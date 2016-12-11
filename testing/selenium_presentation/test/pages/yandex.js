let By = require('selenium-webdriver').By;

function yandexPage(driver) {
    let self = this;

    self.driver = driver;

    self.open = function() {
        return self.driver.get("http://yandex.ru/");
    };

    self.fillQuery = function(query) {
        self.driver.findElement(By.id('text')).sendKeys(query);
    }

    self.search = function() {
        self.driver.findElements(By.className("button_theme_websearch"))
            .then(arr => {
                if (arr.length > 0) {
                    arr[0].click();
                }

            })
    }
    

    self.getMisspellMessage = function() {
        return self.driver.findElements(By.className('misspell__message'))
    }

}

module.exports.yandexPage = yandexPage; 
