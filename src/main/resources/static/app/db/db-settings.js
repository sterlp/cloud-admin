(function(angular) {
    'use strict';
    var app = angular.module('cloudAdmin');
    
    app.component('dbConnectionSettings', {
        bindings: {
            dbId: '@',
            db: "=?"
        },
        controller: ['DbService', '$breadcrumb', '$jsuiState', function ($db, $breadcrumb, $jsuiState) {
            var that = this;
            that.urlValid = null;
            that.error = {};
            that.drivers = [];
            this.$onInit = function(e) {
                console.info("dbSettings init:", e, this.dbId, this.db);
                if (this.db) $breadcrumb.$getLastViewScope().pageLabel = this.db.name;
                else if ("new" !== this.dbId) {
                    this.db = {type: 'GENERIC'};
                    this.reload();
                } else {
                    this.db = {type: 'GENERIC'};
                }
                // load the available drivers
                $db.drivers().then(function(result) {
                    that.drivers = result.data;
                }, handleServerError);
            };
            that.reload = function() {
                that.urlValid = null;
                $db.get(that.dbId).then(function(result) {
                    that.db = result.data;
                    $breadcrumb.$getLastViewScope().pageLabel = that.db.name;
                    that.urlValid = true;
                    that.validateUrlIfPossible();
                }, handleServerError);
            };
            that.validateConnection = function() {
                $db.validate(that.db).then(function(data) {
                    console.info('saved:', data)
                }, handleServerError);
            };
            that.save = function() {
                $db.save(that.db).then(function(data) {
                    console.info('saved:', data)
                }, handleServerError);
            };
            that.canSave = function() {
                if (that.db.name && checkUrl()) return true;
                else return false;
            };
            that.validateUrlIfPossible = function() {
                var urlLooksOkay = checkUrl();
                console.info('validateUrlIfPossible simple check: ', urlLooksOkay);
                if (that.db.driver && urlLooksOkay) {
                    $db.validateUrl(that.db).then(function(result) {
                        that.urlValid = result.data;
                    }, function(error) {
                        console.info('URL validation faild:', result);
                        that.urlValid = null;
                    });
                } else if (!urlLooksOkay) {
                    that.urlValid = false;
                }
            };
            //
            // private functions
            //
            function handleServerError(errorResult) {
                that.error = $jsuiState.parseServerError(errorResult);
            };
            function checkUrl() {
                var result;
                if (that.db.type && that.db.url 
                        && that.db.url.indexOf("jdbc:") >= 0 && that.db.url.indexOf("://") > 0) result = true;
                else result = false;
                return result;
            };
        }],
        templateUrl: ['appConfig', function(appConfig) {
            return appConfig.html('db/db-settings.html');
        }]
    });
})(angular);