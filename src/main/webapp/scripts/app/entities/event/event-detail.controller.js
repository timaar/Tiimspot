'use strict';

angular.module('tiimspotApp')
    .controller('EventDetailController', function ($scope, $rootScope, $stateParams, entity, Event, Adres, PersoonEvent, Comment) {
        $scope.event = entity;
        $scope.load = function (id) {
            Event.get({id: id}, function(result) {
                $scope.event = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:eventUpdate', function(event, result) {
            $scope.event = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
