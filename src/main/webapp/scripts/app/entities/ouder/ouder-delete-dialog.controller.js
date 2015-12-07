'use strict';

angular.module('tiimspotApp')
	.controller('OuderDeleteController', function($scope, $modalInstance, entity, Ouder) {

        $scope.ouder = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ouder.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });