'use strict';

angular.module('tiimspotApp')
	.controller('EventDeleteController', function($scope, $modalInstance, entity, Event) {

        $scope.event = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Event.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });