'use strict';

angular.module('tiimspotApp')
	.controller('PersoonEventDeleteController', function($scope, $modalInstance, entity, PersoonEvent) {

        $scope.persoonEvent = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PersoonEvent.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });