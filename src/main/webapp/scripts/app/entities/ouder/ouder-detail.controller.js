'use strict';

angular.module('tiimspotApp')
    .controller('OuderDetailController', function ($scope, $rootScope, $stateParams, entity, Ouder, Persoon, Comment) {
        $scope.ouder = entity;
        $scope.load = function (id) {
            Ouder.get({id: id}, function(result) {
                $scope.ouder = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:ouderUpdate', function(event, result) {
            $scope.ouder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
