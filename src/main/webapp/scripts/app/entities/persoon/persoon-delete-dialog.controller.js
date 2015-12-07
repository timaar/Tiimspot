'use strict';

angular.module('tiimspotApp')
	.controller('PersoonDeleteController', function($scope, $modalInstance, entity, Persoon) {

        $scope.persoon = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Persoon.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });