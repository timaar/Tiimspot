'use strict';

angular.module('tiimspotApp')
    .controller('AdresDetailController', function ($scope, $rootScope, $stateParams, entity, Adres) {
        $scope.adres = entity;
        $scope.load = function (id) {
            Adres.get({id: id}, function(result) {
                $scope.adres = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:adresUpdate', function(event, result) {
            $scope.adres = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
