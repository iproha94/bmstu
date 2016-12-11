let chai = require('chai');
var assert = chai.assert;

var URL = 'http://yandex.ru';

let webdriver = require('selenium-webdriver');

let driver = new webdriver
    .Builder()
    .forBrowser('chrome')
    .build();

let yandexPage = require('./pages/yandex').yandexPage;
yandexPage = new yandexPage(driver);

describe('GUI Yandex search', () => {
    driver.manage().timeouts().implicitlyWait(5000);

    it('when the search query is blank to display a misspell message', function(done) {

        yandexPage.open()
            .then(() => {
                yandexPage.fillQuery("");
                yandexPage.search();

                return yandexPage.getMisspellMessage()
            })
            .then((arr) => {
                assert.isTrue(arr && arr.length > 0);
                
                done();
            })
    });

    it('when the search query is normal not to display a misspell message', function(done) {
        yandexPage.open()
            .then(() => {
                yandexPage.fillQuery("котики");
                yandexPage.search();

                return yandexPage.getMisspellMessage()
            })
            .then((arr) => {
                assert.isTrue(arr && arr.length == 0);
                
                done();
            })
    });

    after((done) => {
        driver.quit();
        done();
    })
});