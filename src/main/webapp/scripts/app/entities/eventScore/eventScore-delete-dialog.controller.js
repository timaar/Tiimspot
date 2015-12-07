'use strict';

angular.module('tiimspotApp')
	.controller('EventScoreDeleteController', function($scope, $modalInstance, entity, EventScore) {

        $scope.eventScore = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EventScore.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });