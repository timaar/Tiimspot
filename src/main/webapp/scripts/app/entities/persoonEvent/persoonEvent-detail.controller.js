'use strict';

angular.module('tiimspotApp')
    .controller('PersoonEventDetailController', function ($scope, $rootScope, $stateParams, entity, PersoonEvent, EventScore, Event, Persoon, Comment) {
        $scope.persoonEvent = entity;
        $scope.load = function (id) {
            PersoonEvent.get({id: id}, function(result) {
                $scope.persoonEvent = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:persoonEventUpdate', function(event, result) {
            $scope.persoonEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
