(function(angular) {
    'use strict';
    var app = angular.module('cloudAdmin');
    
    app.service('SqlQueryService', ['appConfig', '$http', function(appConfig, $http) {
        this.query = function (search) {
            if (!search || !search.query) throw 'No SQL given';
            return $http({
                method: 'POST',
                url: appConfig.rest('/connections/1/query'),
                data: search
            });
        };
    }]);

    app.component('sqlEditor', {
        controller: ['SqlQueryService', function ($sqlQuery) {
            var that = this;
            that.sqlText="SELECT * FROM sequence;\nSELECT * FROM act_hi_actinst;"
            that.sqlSelected = "";
            that.sqlLine = "";
            that.sqlOptions = {commitType: "ROLLBACK", fetchCount: 50 };

            that.sqlAutocomplete = function(e) {
                console.info("auto complete: ", e);
            };
            that.sqlEnter = function (sql) {
                if (!sql) {
                    if (that.sqlSelected) sql = that.sqlSelected;
                    else sql = that.sqlLine;
                }
                that.sqlOptions.query = sql;
                $sqlQuery.query(that.sqlOptions).then(function(e) {
                    console.info("SQL result: ", e);
                    that.sqlResult = e.data;
                });
            }
        }],
        templateUrl: ['appConfig', function(appConfig) {
            return appConfig.html('sql/editor.html');
        }]
    });
})(angular);