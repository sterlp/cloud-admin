(function(angular) {
    'use strict';
    var app = angular.module('cloudAdmin');

    app.component('dbConnections', {
        controller: ['DbService', '$element', function ($db, $element) {
            var that = this;
            that.error = {};
            that.loading = false;
            this.$onInit = function (event) {
                that.reload();
            };
            that.reload = function() {
                console.info("dbConnections reload ...");
                that.loading = true;
                $db.list().then(function(result) {
                    that.dbs = result.data;
                    that.loading = false;
                }, function (error) {
                    that.error = $jsuiState.parseServerError(errorResult);
                    that.loading = false;
                });
            };
        }],
        templateUrl: ['appConfig', function(appConfig) {
            return appConfig.html('db/db-connections.html');
        }]
    });
})(angular);