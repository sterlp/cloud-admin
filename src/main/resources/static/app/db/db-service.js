(function(angular) {
    'use strict';
    var app = angular.module('cloudAdmin');
    
    app.service('DbService', ['appConfig', '$http', function(appConfig, $http) {
        this.validateUrl = function(data) {
            return $http({
                method: 'POST',
                url: appConfig.rest('db/jdbc/drivers'),
                data: data
            });
        };
        this.drivers = function() {
            return $http({
                method: 'GET',
                url: appConfig.rest('db/jdbc/drivers')
             });
        };
        this.list = function() {
            return $http({
                method: 'GET',
                url: appConfig.rest('connections')
             });
        };
        this.validate = function(con) {
            return $http({
                method: 'POST',
                url: appConfig.rest('connections/validate'),
                data: con
            });
        };
        this.get = function(id) {
            return $http({
                method: 'GET',
                url: appConfig.rest('connections', id)
             });
        };
        this.save = function(con) {
            return $http({
                method: 'POST',
                url: appConfig.rest('connections'),
                data: con
            });
        };
    }]);
})(angular);