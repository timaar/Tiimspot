'use strict';

angular.module('tiimspotApp')
    .controller('EventScoreDetailController', function ($scope, $rootScope, $stateParams, entity, EventScore) {
        $scope.eventScore = entity;
        $scope.load = function (id) {
            EventScore.get({id: id}, function(result) {
                $scope.eventScore = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:eventScoreUpdate', function(event, result) {
            $scope.eventScore = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
