'use strict';

angular.module('tiimspotApp')
    .controller('CommentDetailController', function ($scope, $rootScope, $stateParams, entity, Comment, Event, Ouder, Persoon, PersoonEvent) {
        $scope.comment = entity;
        $scope.load = function (id) {
            Comment.get({id: id}, function(result) {
                $scope.comment = result;
            });
        };
        var unsubscribe = $rootScope.$on('tiimspotApp:commentUpdate', function(event, result) {
            $scope.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
