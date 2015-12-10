'use strict';

angular.module('tiimspotApp')
    .controller('PersoonDetailController', function ($scope, $rootScope, $stateParams, entity, Persoon, Adres, Comment, Ouder) {
        $scope.persoon = entity;
        $scope.load = function (id) {
            Persoon.get({id: id}, function(result) {
                $scope.persoon = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:persoonUpdate', function(event, result) {
            $scope.persoon = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
